/*
 * Copyright 2015 Stuart Kent
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.github.stkent.amplify.tracking;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.AbilityPackage;
import ohos.app.Context;
import ohos.app.ElementsCallback;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;
import ohos.global.configuration.Configuration;
import ohos.rpc.RemoteException;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import com.github.stkent.amplify.App;
import com.github.stkent.amplify.Device;
import com.github.stkent.amplify.Environment;
import com.github.stkent.amplify.IApp;
import com.github.stkent.amplify.IEnvironment;
import com.github.stkent.amplify.feedback.IFeedbackCollector;
import com.github.stkent.amplify.logging.ILogger;
import com.github.stkent.amplify.logging.NoOpLogger;
import com.github.stkent.amplify.prompt.interfaces.IPromptView;
import com.github.stkent.amplify.tracking.interfaces.IAppLevelEventRulesManager;
import com.github.stkent.amplify.tracking.interfaces.IEnvironmentBasedRule;
import com.github.stkent.amplify.tracking.interfaces.IEnvironmentBasedRulesManager;
import com.github.stkent.amplify.tracking.interfaces.IEvent;
import com.github.stkent.amplify.tracking.interfaces.IEventBasedRule;
import com.github.stkent.amplify.tracking.interfaces.IEventListener;
import com.github.stkent.amplify.tracking.interfaces.IEventsManager;
import com.github.stkent.amplify.tracking.managers.AppLevelEventRulesManager;
import com.github.stkent.amplify.tracking.managers.EnvironmentBasedRulesManager;
import com.github.stkent.amplify.tracking.managers.FirstEventTimeRulesManager;
import com.github.stkent.amplify.tracking.managers.LastEventTimeRulesManager;
import com.github.stkent.amplify.tracking.managers.LastEventVersionCodeRulesManager;
import com.github.stkent.amplify.tracking.managers.LastEventVersionNameRulesManager;
import com.github.stkent.amplify.tracking.managers.TotalEventCountRulesManager;
import com.github.stkent.amplify.tracking.rules.HuaweiAppGalleryRule;
import com.github.stkent.amplify.tracking.rules.MaximumCountRule;
import com.github.stkent.amplify.tracking.rules.VersionNameChangedRule;
import com.github.stkent.amplify.utils.AbilityReferenceManager;
import com.github.stkent.amplify.utils.Constants;
import static com.github.stkent.amplify.tracking.PromptInteractionEvent.USER_DECLINED_CRITICAL_FEEDBACK;
import static com.github.stkent.amplify.tracking.PromptInteractionEvent.USER_DECLINED_POSITIVE_FEEDBACK;
import static com.github.stkent.amplify.tracking.PromptInteractionEvent.USER_GAVE_CRITICAL_FEEDBACK;
import static com.github.stkent.amplify.tracking.PromptInteractionEvent.USER_GAVE_POSITIVE_FEEDBACK;

/**
 * Amplify implements InterfaceEventListener.
 */
@SuppressWarnings({"PMD.ExcessiveParameterList", "PMD.CyclomaticComplexity", "checkstyle:parameternumber"})
public final class Amplify implements IEventListener {

    private static final int DEFAULT_USER_GAVE_POSITIVE_FEEDBACK_MAXIMUM_COUNT = 1;
    private static final int DEFAULT_LAST_UPDATE_TIME_COOLDOWN_DAYS = 7;
    private static final int DEFAULT_LAST_CRASH_TIME_COOLDOWN_DAYS = 7;

    private static ILogger sharedLogger = new NoOpLogger();

    public static ILogger getLogger() {
        return sharedLogger;
    }

    public static void setLogger(@NotNull final ILogger logger) {
        sharedLogger = logger;
    }


    @SuppressWarnings("StaticFieldLeak") // We're holding the application context; that's not a problem.
    private static Amplify sharedInstance;

    public static Amplify initSharedInstance(final AbilityPackage abilityPackage) {
        return initSharedInstance(abilityPackage, Constants.DEFAULT_BACKING_SHARED_PREFERENCES_NAME);
    }

    /**
     * Set the initSharedInstance.
     *
     * @param abilityPackage the ability package of the app.
     *
     * @param backingSharedPreferencesName sharedPreferences name.
     */

    public static Amplify initSharedInstance(
            @NotNull final AbilityPackage abilityPackage,
            @NotNull final String backingSharedPreferencesName) {

        synchronized (Amplify.class) {
            sharedInstance = new Amplify(abilityPackage, backingSharedPreferencesName);
        }

        return sharedInstance;
    }

    /**
     * Set the initSharedInstance.
     */
    public static Amplify getSharedInstance() {

        synchronized (Amplify.class) {
            if (sharedInstance == null) {
                throw new IllegalStateException("You must call initSharedInstance before calling getSharedInstance.");
            }
        }
        return sharedInstance;
    }

    private final Context applicationContext;
    private final AbilityReferenceManager abilityReferenceManager;
    private final IEnvironmentBasedRulesManager environmentBasedRulesManager;
    private final IAppLevelEventRulesManager appLevelEventRulesManager;
    private final IEventsManager<Long> firstEventTimeRulesManager;
    private final IEventsManager<Long> lastEventTimeRulesManager;
    private final IEventsManager<Integer> lastEventVersionCodeRulesManager;
    private final IEventsManager<String> lastEventVersionNameRulesManager;
    private final IEventsManager<Integer> totalEventCountRulesManager;

    private boolean alwaysShow;
    private IFeedbackCollector[] positiveFeedbackCollectors;
    private IFeedbackCollector[] criticalFeedbackCollectors;

    // End instance fields
    // Begin constructors

    private Amplify(@NotNull final AbilityPackage abilityPackage, @NotNull final String sharedPrefsName) {
        applicationContext = abilityPackage;
        abilityReferenceManager = new AbilityReferenceManager();
        abilityPackage.registerCallbacks(abilityReferenceManager, new ElementsCallback() {
            @Override
            public void onMemoryLevel(int i) {
                //Method intentionally left blank
            }

            @Override
            public void onConfigurationUpdated(Configuration configuration) {
                //Method intentionally left blank
            }
        });



        final IEnvironment environment = new Environment(applicationContext.getAbilityPackageContext());
        environmentBasedRulesManager = new EnvironmentBasedRulesManager(environment);
        final IApp app = new App(applicationContext);

        DatabaseHelper databaseHelper = new DatabaseHelper(this.applicationContext);
        Preferences prefs = databaseHelper.getPreferences(sharedPrefsName);
        appLevelEventRulesManager = new AppLevelEventRulesManager(new Settings<>(prefs), app);
        firstEventTimeRulesManager = new FirstEventTimeRulesManager(new Settings<>(prefs));
        lastEventTimeRulesManager = new LastEventTimeRulesManager(new Settings<>(prefs));
        lastEventVersionNameRulesManager = new LastEventVersionNameRulesManager(new Settings<>(prefs), app);
        lastEventVersionCodeRulesManager = new LastEventVersionCodeRulesManager(new Settings<>(prefs), app);
        totalEventCountRulesManager = new TotalEventCountRulesManager(new Settings<>(prefs));
    }


    public Amplify setPositiveFeedbackCollectors(@NotNull final IFeedbackCollector... feedbackCollectors) {
        positiveFeedbackCollectors = Arrays.copyOf(feedbackCollectors, feedbackCollectors.length);
        return this;
    }

    public Amplify setCriticalFeedbackCollectors(@NotNull final IFeedbackCollector... feedbackCollectors) {
        criticalFeedbackCollectors = Arrays.copyOf(feedbackCollectors, feedbackCollectors.length);
        return this;
    }
    /**
     * apply all the DefaultRules.
     */

    public Amplify applyAllDefaultRules() throws RemoteException {
        return this
                .addEnvironmentBasedRule(new HuaweiAppGalleryRule())
                .setLastUpdateTimeCooldownDays(DEFAULT_LAST_UPDATE_TIME_COOLDOWN_DAYS)
                .setLastCrashTimeCooldownDays(DEFAULT_LAST_CRASH_TIME_COOLDOWN_DAYS)
                .addTotalEventCountRule(USER_GAVE_POSITIVE_FEEDBACK,
                        new MaximumCountRule(DEFAULT_USER_GAVE_POSITIVE_FEEDBACK_MAXIMUM_COUNT))
                .addLastEventVersionNameRule(USER_GAVE_CRITICAL_FEEDBACK,
                        new VersionNameChangedRule(applicationContext))
                .addLastEventVersionNameRule(USER_DECLINED_CRITICAL_FEEDBACK,
                        new VersionNameChangedRule(applicationContext))
                .addLastEventVersionNameRule(USER_DECLINED_POSITIVE_FEEDBACK,
                        new VersionNameChangedRule(applicationContext));
    }

    public Amplify addEnvironmentBasedRule(@NotNull final IEnvironmentBasedRule rule) {
        environmentBasedRulesManager.addEnvironmentBasedRule(rule);
        return this;
    }

    public Amplify setInstallTimeCooldownDays(final int cooldownPeriodDays) {
        appLevelEventRulesManager.setInstallTimeCooldownDays(cooldownPeriodDays);
        return this;
    }

    public Amplify setLastUpdateTimeCooldownDays(final int cooldownPeriodDays) {
        appLevelEventRulesManager.setLastUpdateTimeCooldownDays(cooldownPeriodDays);
        return this;
    }

    public Amplify setLastCrashTimeCooldownDays(final int cooldownPeriodDays) {
        appLevelEventRulesManager.setLastCrashTimeCooldownDays(cooldownPeriodDays);
        return this;
    }

    public Amplify addTotalEventCountRule(
            @NotNull final IEvent event,
            @NotNull final IEventBasedRule<Integer> rule) {
        totalEventCountRulesManager.addEventBasedRule(event, rule);
        return this;
    }

    /**
     * addFirstEventTimeRule.
     *
     * @param event event.
     *
     * @param rule rule.
     */
    public Amplify addFirstEventTimeRule(
            @NotNull final IEvent event,
            @NotNull final IEventBasedRule<Long> rule) {

        firstEventTimeRulesManager.addEventBasedRule(event, rule);
        return this;
    }

    /**
     * addLastEventTimeRule.
     *
     * @param event event.
     *
     * @param rule rule.
     */
    public Amplify addLastEventTimeRule(
            @NotNull final IEvent event,
            @NotNull final IEventBasedRule<Long> rule) {

        lastEventTimeRulesManager.addEventBasedRule(event, rule);
        return this;
    }

    /**
     * addLastEventVersionCodeRule.
     *
     * @param event event.
     *
     * @param rule rule.
     */
    public Amplify addLastEventVersionCodeRule(
            @NotNull final IEvent event,
            @NotNull final IEventBasedRule<Integer> rule) {

        lastEventVersionCodeRulesManager.addEventBasedRule(event, rule);
        return this;
    }

    public Amplify addLastEventVersionNameRule(
            @NotNull final IEvent event,
            @NotNull final IEventBasedRule<String> rule) {
        lastEventVersionNameRulesManager.addEventBasedRule(event, rule);
        return this;
    }

    // End configuration methods
    // Begin debug configuration methods

    public Amplify setAlwaysShow(final boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
        return this;
    }

    // End debug configuration methods
    // Begin update methods

    @Override
    public void notifyEventTriggered(@NotNull final IEvent event) throws RemoteException {
        sharedLogger.debug(event.getTrackingKey() + " event triggered");
        totalEventCountRulesManager.notifyEventTriggered(event);
        firstEventTimeRulesManager.notifyEventTriggered(event);
        lastEventTimeRulesManager.notifyEventTriggered(event);
        lastEventVersionCodeRulesManager.notifyEventTriggered(event);
        lastEventVersionNameRulesManager.notifyEventTriggered(event);
        if (event == USER_GAVE_POSITIVE_FEEDBACK) {
            final Ability ability = abilityReferenceManager.getValidatedAbility();
            for (final IFeedbackCollector positiveFeedbackCollector : positiveFeedbackCollectors) {
                if (positiveFeedbackCollector.tryCollectingFeedback(
                        ability,
                        new App(applicationContext),
                        new Environment(applicationContext),
                        new Device(applicationContext))) {
                    return;
                }
            }
        } else if (event == USER_GAVE_CRITICAL_FEEDBACK) {
            final Ability ability = abilityReferenceManager.getValidatedAbility();
            for (final IFeedbackCollector criticalFeedbackCollector : criticalFeedbackCollectors) {
                if (criticalFeedbackCollector.tryCollectingFeedback(
                        ability,
                        new App(applicationContext),
                        new Environment(applicationContext),
                        new Device(applicationContext))) {
                    return;
                }
            }
        }
    }

    // End update methods
    // Begin query methods

    /**
     * to check if the prompt is ready.
     *
     * @param promptView promptView.
     */
    public void promptIfReady(@NotNull final IPromptView promptView) throws RemoteException {
        if (!isConfigured()) {
            throw new IllegalStateException("Must finish configuration before attempting to prompt.");
        }
        if (shouldPrompt()) {
            promptView.getPresenter().start();
        }
    }

    /**
     * to check if all the rules allow prompt to be shown.
     */
    public boolean shouldPrompt() {
        return alwaysShow || (
                  appLevelEventRulesManager.shouldAllowFeedbackPrompt()
                && environmentBasedRulesManager.shouldAllowFeedbackPrompt()
                && totalEventCountRulesManager.shouldAllowFeedbackPrompt()
                && firstEventTimeRulesManager.shouldAllowFeedbackPrompt()
                && lastEventTimeRulesManager.shouldAllowFeedbackPrompt()
                && lastEventVersionCodeRulesManager.shouldAllowFeedbackPrompt()
                && lastEventVersionNameRulesManager.shouldAllowFeedbackPrompt());
    }

    private boolean isConfigured() {
        return positiveFeedbackCollectors != null && criticalFeedbackCollectors != null;
    }

}
