package pro.loonatic.demibot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pro.loonatic.demibot.CommandUtils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pro.loonatic.demibot.CommandUtils.FileCheck;

// eff eff em peg
//devs: check s3.java for notes or whatever

public class RecordCommand implements Command {
    static final int seconds = 5; // BUG: bot cannot handle anything above 5 for now -.-
    static final int ms = seconds * 1000;

    public void process(MessageReceivedEvent event, List<String> args) throws Exception {

        //ngl, we can use Arrays.AsList here instead of this stupid for each loop crap

        MessageChannel channel = event.getChannel();
        ArrayList<String> AudioRecord = new ArrayList<String>();
        ArrayList<String> VideoRecord = new ArrayList<String>();
        ArrayList<String> ConvertAV = new ArrayList<String>();

        String audioargs = "cmd.exe /c ffmpeg -f dshow -rtbufsize 300M -i audio=\"virtual-audio-capturer\" -t "+ seconds +" ./screencapturevideo_audio.mkv -y"; //soon we should have a config file to modify time,
        String[] argArr1 = audioargs.split(" ");
        String vidargs =   "cmd.exe /c ffmpeg -f gdigrab -rtbufsize 300M -framerate 30 -t "+ seconds +" -draw_mouse 0 -i desktop ./screencapturevideo_video.mkv -y"; // doing + gdigrab + rn is broken
        String[] argArr2 = vidargs.split(" ");
        String convert = "cmd.exe /c ffmpeg -i ./screencapturevideo_video.mkv -i ./screencapturevideo_audio.mkv ./screencapturevideo.mp4 -y";
        String[] argArr3 = convert.split(" ");


        //WARNING: THIS DOESN'T WORK --> VideoRecord.add(commandargs);

            for(String elements : argArr1) {
                AudioRecord.add(elements);
            }
            for(String elements : argArr2) {
                VideoRecord.add(elements);
            }
            for(String elements : argArr3) {
                ConvertAV.add(elements);
            }

        channel.sendFile(recordVid(AudioRecord, VideoRecord, ConvertAV)).queue();

        }

        public File recordVid(ArrayList<String> audio, ArrayList<String> video, ArrayList<String> convert) {
            try {
                if(FileCheck()) {
                    System.out.println(audio.size());
                    Robot robot = new Robot();

                    File recFile = new File("screencapturevideo.mp4");

                    if(!recFile.exists()) {
                        recFile.createNewFile();
                        System.out.println("not here");
                    }

                    ProcessBuilder[] Proc = new ProcessBuilder[3];

                    Proc[0] = new ProcessBuilder(audio);
                    Proc[1] = new ProcessBuilder(video);
                    Proc[2] = new ProcessBuilder(convert);

                    Process sAudio = Proc[0].start();
                    Process sVideo = Proc[1].start();
                    int e = 0, g = 0;

                    while(sAudio.isAlive() && sVideo.isAlive()) {
                        System.out.println(e++);
                        robot.delay(1000);
                     //   if(e > seconds) { // ffmpeg is stoopod -- sanity check for longer duration
                     //       sAudio.destroy();
                     //   }
                    }

                    //Process sVideo = Proc[1].start();
                    /*
                    while(sVideo.isAlive()) {
                        System.out.println("video" +g++);
                        robot.delay(1000);
                     //   if(g > seconds) { // ffmpeg is stoopod -- sanity check for longer duration
                     //       sVideo.destroy();
                     //   }
                    }
                    */

                    Process sConvert = Proc[2].start();

                    robot.delay(5 * 1000); // temp >_>

                    return recFile;
                    //channel.sendFile(recFile).queue();
                } else {
                    recordFailure(null);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                recordFailure(e);
            }
            return null;
        }
        private String recordFailure(Exception e) {
            if (!e.toString().isEmpty()) {
                return "Couldn't send video! :( ***Exception: ***" + e.toString(); //this would never get called, and it may cause errors.
            } else {
                return "WARNING: File Checker failed! Please ensure you have all of your dependencies in the right location.";
            }
        }
    }
