package com.sandbox.main.dbconfig;

import com.sandbox.services.api.dao.Deleter;
import com.sandbox.services.api.dao.Finder;
import com.sandbox.services.api.general.HealthChecker;
import com.sandbox.services.api.dao.Saver;
import com.sandbox.services.api.dao.SetupData;
import com.sandbox.services.domain.Person;

public interface DBConfig {
    Finder<Person> getFinderPerson();
    Saver<Person> getSaverPerson();
    Deleter getDeleterPerson();
    HealthChecker getHealthChecker();
    SetupData getSetupData();
}
