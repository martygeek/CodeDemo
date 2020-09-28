package us.martypants.hca;

import android.app.Application;
import android.content.Context;

import us.martypants.hca.modules.DaggerUserComponent;
import us.martypants.hca.modules.NetModule;
import us.martypants.hca.modules.UserComponent;
import us.martypants.hca.modules.UserModule;

public class App extends Application {

    private static final String TAG = "App";

    private static App app;
    private UserComponent mUserComponent;

    public static App getApp() {
        return app;
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mUserComponent == null) {
            mUserComponent = DaggerUserComponent.builder()
                    .userModule(new UserModule())
                    .netModule(new NetModule(this, getNetworkEndpoint()))
                    .build();
        }
        app = this;

        mUserComponent.inject(this);
    }




    private String getNetworkEndpoint() {
        return  "http://api.stackexchange.com";
    }

    /**
     * Get the UserComponent to inject a class with
     *
     * @return a UserComponent
     */
    public UserComponent getUserComponent() {
        return mUserComponent;
    }

    /**
     * This is used to inject a component for testing if
     * required.
     *
     * @param component a usercomponent for testing with
     */
    public void setUserComponent(UserComponent component) {
        mUserComponent = component;
    }
}