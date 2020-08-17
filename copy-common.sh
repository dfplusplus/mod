# makes them deletable
chmod -R +w forge/src/main/java/com/github/dfplusplus/common

# removes old common files
rm -rf forge/src/main/java/com/github/dfplusplus/common

# copies the files
cp -r common/src/main/java/com forge/src/main/java/

# makes them readonly
chmod -R -w forge/src/main/java/com/github/dfplusplus/common