UPDATE presidentrace SET democrat = random()*(9000) + 1;
UPDATE presidentrace SET republican = random()*(9000) + 1;

UPDATE houserace SET democrat = random()*(9000) + 1;
UPDATE houserace SET republican = random()*(9000) + 1;

UPDATE senaterace SET democrat = random()*(9000) + 1;
UPDATE senaterace SET republican = random()*(9000) + 1;



UPDATE presidentrace SET democrat = 0;
UPDATE presidentrace SET republican = 0;

UPDATE houserace SET democrat = 0;
UPDATE houserace SET republican = 0;

UPDATE senaterace SET democrat = 0;
UPDATE senaterace SET republican = 0;
