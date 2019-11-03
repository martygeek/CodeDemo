package us.martypants.rightpointdemo.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import us.martypants.rightpointdemo.App;
import us.martypants.rightpointdemo.managers.DataManager;
import us.martypants.rightpointdemo.network.DataManagerAPI;
import us.martypants.rightpointdemo.repository.ImdbRepository;


@Module
public class UserModule {

    @Singleton
    @Provides
    DataManagerAPI getServerAPI(Retrofit retrofit) {
        return retrofit.create(DataManagerAPI.class);
    }


    @Singleton
    @Provides
    DataManager getDataManager(DataManagerAPI api) {
        return new DataManager(api);
    }


    @Singleton
    @Provides
    ImdbRepository getImdbRepository(App app) {
        return new ImdbRepository(app);
    }
}
