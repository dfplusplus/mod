echo Building the other jars from $1

# removes previous build files, files in the top directory NOT matching the name in $1
find build/libs/*.* ! -name $1.jar -exec rm -f {} \;

# ensure the dfplusplus folder exists
mkdir -p build/libs/assets/dfplusplus

# copies permission files in
# shellcheck disable=SC2225
cp -a permissions/. build/libs/assets/dfplusplus

# cds to make the next bit more readable
# shellcheck disable=SC2164
cd build/libs

# creates new jar then copies the file in
cp $1.jar $1-admin.jar
jar uf $1-admin.jar assets/dfplusplus/admin_permissions

cp $1.jar $1-mod.jar
jar uf $1-mod.jar assets/dfplusplus/mod_permissions

cp $1.jar $1-support.jar
jar uf $1-support.jar assets/dfplusplus/support_permissions

