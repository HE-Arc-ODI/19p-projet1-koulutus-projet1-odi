package ch.hearc.odi.koulutus.log;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {

  private static MyLogger instance = null;

  private Logger logger;

  private MyLogger() {
    logger = Logger.getLogger(MyLogger.class.getName());
    Handler[] handlers = logger.getHandlers();

    for(int i = 0; i<handlers.length; i++){
      logger.removeHandler(handlers[i]);
    }

    try{
      // On enregistre le fichier de log à la racine du dossier du projet Netbeans
      FileHandler fileHandler = new FileHandler("19p-projet1.log");
      fileHandler.setFormatter(new java.util.logging.SimpleFormatter());
      logger.addHandler(fileHandler);
    }catch(Exception e){
      throw new RuntimeException("Erreur pendant la création du logger", e);
    }
  }

  public static MyLogger getInstance(){
    if(instance == null){
      instance = new MyLogger();
    }

    return instance;
  }

  /**
   * Cette méthode ne sert que de raccourci pour éviter de devoir faire MyLogger.getInstance().getLogger().log(...).
   * @param level
   * @param message
   */
  public void log(Level level, String message){
    logger.log(level, message);
  }

  public void log(Level level, String message, Throwable throwable){
    logger.log(level, message, throwable);
  }

}

