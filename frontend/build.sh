vue-cli-service build;
rm -rf ../backend/src/main/resources/public;
mkdir ../backend/src/main/resources/public;
mv ./dist/* '../backend/src/main/resources/public';
rm -rf ./dist;

