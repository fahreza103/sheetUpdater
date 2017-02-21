eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa_fahreza

cd $DEVELOPMENT_APP_PATH
git status
git fetch origin
git checkout $DEVELOPMENT_BRANCH
git pull origin $DEVELOPMENT_BRANCH

#STOP APPLICATION
curl -X POST $APP_HOST:$APP_PORT/shutdown

#START APPLICATION
mvn spring-boot:run