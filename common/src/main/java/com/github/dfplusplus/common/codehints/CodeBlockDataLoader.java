// BY lukecashwell

package com.github.dfplusplus.common.codehints;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.ListNBT;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CodeBlockDataLoader {

    private static String getCodeBlockInfo() {
        // Load current version
        int version = 0;
        String versionFile = readFile("version.dfpp");
        if(versionFile != null) {
            try {
            version = Integer.parseInt(JsonToNBT.getTagFromJson(versionFile).getString("version"));
            } catch (Exception e){}
        }

        // Find newest version
        int newVersion = 0;
        try {
            System.out.println("Checking for codeblock data update...");
            URL urlVersion = new URL("https://dfplusplus.glitch.me/version");
            String urlString = readURL(urlVersion);
            newVersion = Integer.parseInt(JsonToNBT.getTagFromJson(urlString).getString("version"));
;       } catch (Exception e) { e.printStackTrace(); }

        // Load code block info or update if version is low
        String codeBlockInfo = null;
        if(version < newVersion) {
            try {
                System.out.println("Downloading latest code block data...");
                URL urlVersion = new URL("https://dfplusplus.glitch.me/data");
                codeBlockInfo = readURL(urlVersion);
            } catch (Exception e) { System.out.println("Error, unable to download codeblock data."); }

            if(codeBlockInfo != null) {
                System.out.println("Finished downloading.");
                writeFile("data.dfpp", codeBlockInfo);
                writeFile("version.dfpp", "{\"version\":\"" + newVersion + "\"}");
            }
        }

        // Just gets the info if you don't need new info or can't download the new info
        if(codeBlockInfo == null) {
            String contents = readFile("data.dfpp");
            if(contents != null) {
                codeBlockInfo = contents;
            }
        }

        return codeBlockInfo;
    }

    public static HashMap<String, ArrayList<String>> loadCodeBlockInfo() {
        HashMap<String, ArrayList<String>> codeBlockInfo = new HashMap<>();


        String data = getCodeBlockInfo();
        if(data != null) {
            try {

                HashMap<String, String> argumentTypes = new HashMap<String, String>();

                argumentTypes.put("VARIABLE", m("&6Var"));
                argumentTypes.put("LIST", m("&2List"));
                argumentTypes.put("TEXT", m("&eTxt"));
                argumentTypes.put("NUMBER", m("&cNum"));
                argumentTypes.put("LOCATION", m("&aLoc"));
                argumentTypes.put("PARTICLE", m("&9Particle"));
                argumentTypes.put("ITEM", m("&9Item"));
                argumentTypes.put("BLOCK", m("&5Block"));
                argumentTypes.put("BLOCK_TAG", m("&dBlock Tag"));
                argumentTypes.put("ANY_TYPE", m("&dAny"));



                ListNBT actions = JsonToNBT.getTagFromJson(data).getList("actions", 10);
                for(int i = 0; i < actions.size(); i++) {
                    CompoundNBT action = actions.getCompound(i);
                    ArrayList<String> infoLines = new ArrayList<>();
                    String name = action.getString("name");
                    String codeblockName = action.getString("codeblockName");

                    CompoundNBT icon = action.getCompound("icon");

                    infoLines.add(icon.getString("name"));

                    ListNBT arguments = icon.getList("arguments", 10);
                    if(!arguments.isEmpty()) {
                        StringBuilder argLine = new StringBuilder();
                        for (int j = 0; j < arguments.size(); j++) {
                            CompoundNBT arg = arguments.getCompound(j);
                            String argName;
                            {
                                ListNBT argNameList = arg.getList("description", 8);
                                StringBuilder argNameBuilder = new StringBuilder();
                                for (int k = 0; k < argNameList.size(); k++) {
                                    argNameBuilder.append(argNameList.getString(k)).append(' ');
                                }
                                if(argNameBuilder.length() > 0) {
                                    argNameBuilder.setLength(argNameBuilder.length() - 1);
                                }
                                argName = argNameBuilder.toString().toLowerCase().replace(' ', '_');
                            }

                            argLine.append(m("&e" + argName + "&7: "));
                            argLine.append(argumentTypes.get(arg.getString("type")));

                            byte isPlural = arg.getByte("plural");
                            byte isOptional = arg.getByte("optional");
                            if (isPlural != 0) {
                                argLine.append("(s)");
                            }
                            if (isOptional != 0) {
                                argLine.append(m("&f*"));
                            }
                            if (j != arguments.size() - 1) {
                                argLine.append(m("&7, "));
                            }
                            if (argLine.length() > 50) {
                                infoLines.add(argLine.toString());
                                argLine.setLength(0);
                            }
                        }
                        infoLines.add(argLine.toString());
                    }

                    infoLines.add("");
                    infoLines.add(m("&7Description &e&l>"));
                    ListNBT description = icon.getList("description", 8);
                    for(int j = 0; j < description.size(); j++) {
                        infoLines.add(description.getString(j));
                    }
                    infoLines.add("");
                    infoLines.add("");

                    ListNBT example = icon.getList("example", 9);
                    if(!example.isEmpty()) {
                        infoLines.add(m("&7Examples &e&l>"));
                        for(int j = 0; j < example.size(); j++) {
                            infoLines.add(example.getString(j));
                        }
                    }

                    ListNBT additionalInfo = icon.getList("additionalInfo", 9);
                    if(!additionalInfo.isEmpty()) {
                        infoLines.add(m("&7Additional Info &e&l>"));
                        for(int j = 0; j < additionalInfo.size(); j++) {
                            ListNBT info = additionalInfo.getList(j);
                            for(int k = 0; k < info.size(); k++) {
                                infoLines.add(info.getString(k));
                            }
                            infoLines.add("");
                        }
                    }

                    codeBlockInfo.put(codeblockName + ":" + name, infoLines);
                    ListNBT alias = action.getList("aliases", 8);
                    for(int j = 0; j < alias.size(); j++) {
                        codeBlockInfo.put(codeblockName + ":" + alias.getString(j), infoLines);
                    }

                }

            } catch (Exception ex ){
                ex.printStackTrace();
            }
        }

        return codeBlockInfo;
    }


    // ------------------------------------------------------------------------------------------ //
    //                                                                                            //
    // These are a bunch of helper function things that I used throughout the code above.         //
    // They originally came from a bunch of different classes, but this should suffice for now.   //
    //                                                                                            //
    // ------------------------------------------------------------------------------------------ //

    private static String readURL(URL url) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder file = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            file.append(line).append("\n");
        }
        in.close();

        return file.toString();
    }

    private static String readFile(String path) {
        FileReader fr;
        StringBuilder string = new StringBuilder();
        try {
            fr = new FileReader(path);
            int i;
            while ((i = fr.read()) != -1) {
                string.append((char)i);
            }
            fr.close();
        } catch (Exception e) {
            System.err.println("Unable to load file: " + e);
            return null;
        }
        return string.toString();
    }

    private static void writeFile(String path, String info) {
        File file = new File(path);

        try {
            if(!file.exists()) {
                file.createNewFile();
            }

            file.setWritable(true);
            file.setReadable(true);

            if(!file.canWrite()) {
                System.err.println();
                System.err.println("FILE UTILS : Unable to write to file.");
                System.err.println("FILE UTILS : File : \n" + path);
                System.err.println();
            }
            FileWriter fw = new FileWriter(file.getAbsolutePath());
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(info);
            writer.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    private static String m(String input) {
        return input.replace('&', '\u00a7');
    }

    // ------------------------------------------------------------------------------------------ //
}
