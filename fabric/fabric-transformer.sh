for file in "$@"
do
  echo $file
  sed -i 's/net.minecraft.client.settings./net.minecraft.client.options./g' $file
done