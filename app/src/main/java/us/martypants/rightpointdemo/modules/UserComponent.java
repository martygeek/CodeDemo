package us.martypants.rightpointdemo.modules;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import us.martypants.rightpointdemo.App;
import us.martypants.rightpointdemo.ImdbViewmodel;
import us.martypants.rightpointdemo.MainActivity;
import us.martypants.rightpointdemo.ImdbRepository;

@Singleton
@Component(modules = {UserModule.class, NetModule.class})
public interface UserComponent {

    // Pass on any provided objects to subclasses.
    // If you are writing tests and can't get things injected
    // add to this list.
    Gson gson();


    void inject(App app);

    void inject(MainActivity mainActivity);
    void inject(ImdbViewmodel viewmodel);
    void inject(ImdbRepository imdbRepository);

}
