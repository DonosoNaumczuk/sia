REPOSITORY_NAME=gps
mkdir output
cd ./../gps && mvn package | tee ./../testGps/output/student_package_result.txt
cd ./../testGps
mvn install:install-file -Dfile=./../gps/target/gps-1.0.jar | tee output/student_package_installing_result.txt
mvn package | tee output/test_result.txt
