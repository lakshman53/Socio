package lib;

import dagger.Module;
import lib.db.sqlite.SQLiteDatabaseFactory;
import lib.security.UserHelper;

@Module(injects = {
        SQLiteDatabaseFactory.class,
       // BitmapCacheHolder.class,
        UserHelper.class,

       // GooglePlusLoginFragment.class
    })
public class GenModule {

}
