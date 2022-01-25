package com.minlessika.membership.takes;

import com.supervisor.sdk.app.info.AppInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * Fake app info.
 * @since 0.1
 */
class FakeAppInfo implements AppInfo {


    @Override
    public boolean activeRecaptcha() {
        return false;
    }

    @Override
    public String home() {
        return "/";
    }

    @Override
    public String recaptchaSiteKey() {
        return null;
    }

    @Override
    public String currentModule() {
        return "my-supervisor";
    }

    @Override
    public String recaptchaSecretKey() {
        return null;
    }

    @Override
    public String sharedDomain() {
        return "localhost";
    }

    @Override
    public String minlessikaDomain() {
        return "localhost";
    }

    @Override
    public boolean activeMailChimp() {
        return false;
    }

    @Override
    public Map<String, String> moduleLinks() {
        return new HashMap<>();
    }
}
