package us.martypants.hca.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import us.martypants.hca.App;
import us.martypants.hca.managers.DataManager;
import us.martypants.hca.network.DataManagerAPI;
import us.martypants.hca.repository.SoRepository;


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
    SoRepository getImdbRepository(App app) {
        return new SoRepository(app);
    }
}
