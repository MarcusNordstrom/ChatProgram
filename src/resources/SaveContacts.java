package resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveContacts {
	private int index = 0;
	private String folderpath = "savedContacts/";
	private String format = ".txt";
	
	public void saveToList(User from, User to) {
		if(searchLists(from) != null && !checkIfUserExists(to)) {
			try(FileWriter fw = new FileWriter(searchLists(from), true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				    out.println(to.getName());
				    out.close();
				} catch (IOException e) {
					e.fillInStackTrace();
				}
		}else if(searchLists(from) == null) {
			try(FileWriter fw = new FileWriter(new File(folderpath + from.getName() + format), true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				    out.println(from.getName());
				    out.println(to.getName());
				    out.close();
				} catch (IOException e) {
				    e.fillInStackTrace();
				}
		}
	}
	public ArrayList<String> read(User from) {
		ArrayList<String> returnArr = new ArrayList<String>();
		try {
			Scanner scan = new Scanner(searchLists(from));
			scan.next();
			while(scan.hasNext()) {
				returnArr.add(scan.next());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return returnArr;
	}
	public File searchLists(User from) {
		File dir = new File(folderpath);
		Scanner scan;
		for(File file : dir.listFiles()) {
			try {
				scan = new Scanner(file);
				while(scan.hasNext()) {
					if(scan.next().equals(from.getName())) {
						return file;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public boolean checkIfUserExists(User to) {
		File dir = new File(folderpath);
		Scanner scan;
		for(File file : dir.listFiles()) {
			try {
				scan = new Scanner(file);
				while(scan.hasNext()) {
					if(scan.next().equals(to.getName())) {
						return true;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public static void main(String[] args) {
		User bando = new User("Bando", null);
		User markvatr = new User("Anna", null);
		SaveContacts demo = new SaveContacts();
		demo.saveToList(bando, markvatr);
		for(String users : demo.read(bando)) {
			System.out.println(users);
		}
		
	}
}
