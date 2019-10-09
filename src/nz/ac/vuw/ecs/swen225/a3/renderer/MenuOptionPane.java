package nz.ac.vuw.ecs.swen225.a3.renderer;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.recnplay.RecordAndPlay;

/**
 * MenuOptions extends JMenuBar and is responsible for hosting the options in the menu bar at the
 * top of the GUI.
 * @author Zac Scott 300447976, Harrison Cook 300402048, Jacob Fraser.
 */
class MenuOptionPane extends JMenuBar {

  /**
   * Default serial number.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor: Creates MenuBar and adds all game relevant components.
   */
  MenuOptionPane(ChapsChallenge application) {
    // Set the Size of the Control panel
    Gui gui = application.getGui();
    setPreferredSize(new Dimension(gui.getScreenWidth(), gui.getMenuHeight()));
    setFont(new Font("Serif", Font.BOLD, 18));

    // Create the file menu
    JMenu file = new JMenu("File");

    // Create the quit button
    JMenuItem closeItem = new JMenuItem("Quit");
    closeItem.addActionListener(arg0 -> System.exit(0));
    file.add(closeItem);

    // Create the save button
    JMenuItem saveItem = new JMenuItem("Save");
    saveItem.addActionListener(arg0 -> application.saveGame());
    file.add(saveItem);

    //create the load button
    JMenuItem loadItem = new JMenuItem("Load");
    loadItem.addActionListener(arg0 -> application.loadGame());
    file.add(loadItem);

    // Create the game menu
    JMenu game = new JMenu("Game");

    // Create the previous level button
    JMenuItem previousItem = new JMenuItem("Previous Level");
    previousItem.addActionListener(arg0 -> application.previousLevel());
    game.add(previousItem);

    // Create the Pause button
    JMenuItem pauseItem = new JMenuItem("Pause");
    pauseItem.addActionListener(arg0 -> application.pauseGame());
    game.add(pauseItem);

    //Create Recording dropdown
    JMenu recording = new JMenu("Recording Options");

    // Create the startRecording level button
    JMenuItem startRecording = new JMenuItem("Start Recording");
    startRecording.addActionListener(arg0 -> RecordAndPlay.newSave(application, "record.txt"));
    recording.add(startRecording);

    // Create the saveRecording button
    JMenuItem saveRecording = new JMenuItem("Save Recording");
    saveRecording.addActionListener(arg0 -> RecordAndPlay.saveGame());
    recording.add(saveRecording);

    //Create the loadRecording button
    JMenuItem loadRecording = new JMenuItem("Load Recording");
    //TODO: allow choice of load file
    loadRecording.addActionListener(arg0 -> RecordAndPlay.loadRecording("record.txt", application));
    recording.add(loadRecording);

    // Create step button
    JMenuItem step = new JMenuItem("Step Recording");
    step.addActionListener(arg0 -> RecordAndPlay.step(application));

    JMenuItem tenth = new JMenuItem("0.1s");
    tenth.addActionListener(arg0 -> RecordAndPlay.setDelay(100));

    JMenuItem fifth = new JMenuItem("0.2s");
    fifth.addActionListener(arg0 -> RecordAndPlay.setDelay(200));

    JMenuItem half = new JMenuItem("0.5s");
    half.addActionListener(arg0 -> RecordAndPlay.setDelay(500));

    JMenuItem one = new JMenuItem("1s");
    one.addActionListener(arg0 -> RecordAndPlay.setDelay(1000));

    // Create replay speed dropdown
    JMenu playbackSpeed = new JMenu("Playback speed");

    playbackSpeed.add(tenth);
    playbackSpeed.add(fifth);
    playbackSpeed.add(half);
    playbackSpeed.add(one);

    // Create playback button
    JMenuItem playback = new JMenuItem("Playback Recording");
    playback.addActionListener(arg0 -> RecordAndPlay.run(application));

    // Add components
    add(file);
    add(game);
    add(recording);
    add(step);
    add(playbackSpeed);
    add(playback);
  }
}
