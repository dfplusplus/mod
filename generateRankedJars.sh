echo Building the other jars from $1

# removes previous build files, files in the top directory NOT matching the name in $1
find build/libs/*.* ! -name $1.jar -exec rm -f {} \;

# ensure the dfadmintools folder exists
mkdir -p build/libs/assets/dfadmintools

# copies permission files in
# shellcheck disable=SC2225
cp -a permissions/. build/libs/assets/dfadmintools

# cds to make the next bit more readable
# shellcheck disable=SC2164
cd build/libs

# creates new jar then copies the file in
cp $1.jar $1-admin.jar
jar uf $1-admin.jar assets/dfadmintools/admin_permissions

cp $1.jar $1-mod.jar
jar uf $1-mod.jar assets/dfadmintools/mod_permissions

cp $1.jar $1-support.jar
jar uf $1-support.jar assets/dfadmintools/support_permissions

