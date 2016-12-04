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

UPDATE presidentrace SET demCandidate = NULL;
UPDATE presidentrace SET repCandidate = NULL;

UPDATE houserace SET demCandidate = NULL;
UPDATE houserace SET repCandidate = NULL;

UPDATE senaterace SET demCandidate = NULL;
UPDATE senaterace SET repCandidate = NULL;