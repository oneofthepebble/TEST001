package com.project.codesend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CodeSend {

	public List list;
	public String ss=null;

	int intcode=0;
	char charcode;
	char Charcode;

	public CodeSend(){
		list=new ArrayList();
		for(int i=0;i<=1;i++) {
			list.add((char)((Math.random()*26)+97));
			list.add((char)((Math.random()*26)+65));
			list.add((int)(Math.random()*10));
			Collections.shuffle(list);
		}
	}
	public String cd1() {
		ss=""+list.get(0)+list.get(1)+list.get(2)+list.get(3)+list.get(4)+list.get(5);
		return ss;
	}
}
