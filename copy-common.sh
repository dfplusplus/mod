# makes them deletable
chmod -R +w forge/src/main/java/com/github/dfplusplus/common
chmod -R +w fabric/src/main/java/com/github/dfplusplus/common

# removes old common files
rm -rf forge/src/main/java/com/github/dfplusplus/common
rm -rf fabric/src/main/java/com/github/dfplusplus/common

# copies the files
cp -r common/src/main/java/com forge/src/main/java/
cp -r common/src/main/java/com fabric/src/main/java/

# makes them readonly
chmod -R -w forge/src/main/java/com/github/dfplusplus/common
chmod -R -w fabric/src/main/java/com/github/dfplusplus/common