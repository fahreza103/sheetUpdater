eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa_fahreza

cd $DEVELOPMENT_APP_PATH
git status
git fetch origin
git checkout $DEVELOPMENT_BRANCH
git pull origin $DEVELOPMENT_BRANCH

./mvnw -N -q io.takari:maven:wrapper
./mvnw install -DskipTests

