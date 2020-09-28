package us.martypants.hca.modules;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import us.martypants.hca.App;
import us.martypants.hca.MainActivity;
import us.martypants.hca.SoViewmodel;
import us.martypants.hca.repository.SoRepository;

@Singleton
@Component(modules = {UserModule.class, NetModule.class})
public interface UserComponent {

    // Pass on any provided objects to subclasses.
    // If you are writing tests and can't get things injected
    // add to this list.
    Gson gson();


    void inject(App app);

    void inject(MainActivity mainActivity);
    void inject(SoViewmodel viewmodel);
    void inject(SoRepository imdbRepository);

}
