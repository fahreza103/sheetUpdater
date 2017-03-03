eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa_fahreza

cd $DEVELOPMENT_APP_PATH
git status
git fetch origin
git checkout $DEVELOPMENT_BRANCH
git pull origin $DEVELOPMENT_BRANCH

chmod +x mvnw
mvn -N io.takari:maven:wrapper
./mvnw install -DskipTests -Dmaven.javadoc.skip=true -B -V

