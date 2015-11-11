package serveur;

import java.util.HashMap;
import java.util.Map;

public class NamedThread extends Thread{
	
	
		 
		/** Map d'association Nom/Thread */
		private static final Map<String,NamedThread> threads = new HashMap<String, NamedThread>();
	 
		/** Méthode static permettant de récupérer un Thread par son nom */
		public static NamedThread getByName(String name) {
			return threads.get(name);
		}
	 
		/**
		 * Méthode privée qui enregistre le thread dans la Map
		 * (utilisé par le constructeur seulement)
		 */
		public void register() {
			threads.put(this.getName(), this);
		}
	 
		/**
		 * Supprime le thread de la Map d'association
		 */
		public void unregister() {
			threads.remove(this.getName());
		}
	 
		/**
		 * On redéfinit finalize() 
		 */
		@Override
		protected void finalize() throws Throwable {
			unregister();
			super.finalize();
		}
	 
		/*public NamedThread(Runnable target, String name) {
			super(target, name);
			register();
		}
	 
		public NamedThread(String name) {
			super(name);
			register();
		}*/
		
		public NamedThread() {
			
		}
	

}
