
/*
  This block below will randomly generate voting data for each race in each cell of the SQL database
 */
UPDATE presidentrace SET democrat = random()*(9000) + 1;
UPDATE presidentrace SET republican = random()*(9000) + 1;

UPDATE houserace SET democrat = random()*(9000) + 1;
UPDATE houserace SET republican = random()*(9000) + 1;

UPDATE senaterace SET democrat = random()*(9000) + 1;
UPDATE senaterace SET republican = random()*(9000) + 1;


/*
  This block below will clear all the values from the database if executed
 */
UPDATE presidentrace SET democrat = 0;
UPDATE presidentrace SET republican = 0;

UPDATE houserace SET democrat = 0, demcandidate = "";
UPDATE houserace SET republican = 0, repcandidate = "";

UPDATE senaterace SET democrat = 0, demcandidate = "";
UPDATE senaterace SET republican = 0, repcandidate = "";
