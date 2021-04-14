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

	private int t = 0; // ���ֲ���ʱ��
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
		} // �����Ƶ������

	}

	public void player() {
		AudioInputStream ais;
		AudioFormat baseFormat;
		DataLine.Info info;
		ais = bgm;
		baseFormat = ais.getFormat(); // ָ�����������ض����ݰ���
		info = new DataLine.Info(SourceDataLine.class, baseFormat);
		SourceDataLine line = null;  //�������ߴ����ֽڵĻ��岢���䴫�ݵ���Ƶ��
		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(baseFormat);
			// �򿪾���ָ����ʽ���У�������ʹ�л�����������ϵͳ��Դ����ÿɲ�����
			line.start();// ����������ִ������ I/O

			int BUFFER_SIZE = 4000 * 4;
			int intBytes = 0;
			byte[] audioData = new byte[BUFFER_SIZE]; // ��Ƶ��������
			while (intBytes != -1 && (playing == false)) {
				intBytes = ais.read(audioData, 0, BUFFER_SIZE);
				// ����Ƶ����ȡָ������������������ֽڣ����������������ֽ������С�
				if (intBytes >= 0) {
					line.write(audioData, 0, intBytes);// ͨ����Դ�����н���Ƶ����д���Ƶ����
					t += 1;
				}
				//System.out.println(t);
			}
		} catch (LineUnavailableException | IOException e1) {

			e1.printStackTrace();
		}
	}

}
