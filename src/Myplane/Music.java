package Myplane;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {

	private int t = 0; // 音乐播放时间
	AudioInputStream bgm;
	private boolean playing = false;

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public Music() {
		try {
			bgm = AudioSystem.getAudioInputStream(new File("D:\\bgmusic.wav"));
		} catch (UnsupportedAudioFileException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} // 获得音频输入流

	}

	public void player() {
		AudioInputStream ais;
		AudioFormat baseFormat;
		DataLine.Info info;
		ais = bgm;
		baseFormat = ais.getFormat(); // 指定声音流中特定数据安排
		info = new DataLine.Info(SourceDataLine.class, baseFormat);
		SourceDataLine line = null;  //该数据线处理字节的缓冲并将其传递到混频器
		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(baseFormat);
			// 打开具有指定格式的行，这样可使行获得所有所需的系统资源并变得可操作。
			line.start();// 允许数据行执行数据 I/O

			int BUFFER_SIZE = 4000 * 4;
			int intBytes = 0;
			byte[] audioData = new byte[BUFFER_SIZE]; // 音频数据数组
			while (intBytes != -1 && (playing == false)) {
				intBytes = ais.read(audioData, 0, BUFFER_SIZE);
				// 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
				if (intBytes >= 0) {
					line.write(audioData, 0, intBytes);// 通过此源数据行将音频数据写入混频器。
					t += 1;
				}
				//System.out.println(t);
			}
		} catch (LineUnavailableException | IOException e1) {

			e1.printStackTrace();
		}
	}

}
