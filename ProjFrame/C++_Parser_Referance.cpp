#include <fstream>
#include <iostream>
#include <string.h>
#include <vector>

using namespace std;

class loads {

	
public:
	//Function getvaluec
	int getvaluec(string ret[128],string line,int curr){
		//curr = main line pointer, subcurr = line pointer used for selecting words
		int count = 0;
		int subcurr = 0;
		//skip whitespace by incrementing the line pointer
		while(curr<line.length()){
			while(line[curr]==' '){
				curr+=1;
			}
			//reset subcurr and increment until a whitespace is reached
			subcurr=0;
			while(line[curr]!=' ' and line[curr]!='\0'){
				curr+=1;
				subcurr +=1;
			}
			//cuts line into words by starting index index curr(end of word)-subcurr(size), and size by subcurr
			ret[count] = line.substr(curr-subcurr,subcurr);
			//go into whitespace
			count+=1;
		}
	return count;
	}
	
	bool loadobj(string file){
		//opens file from "file", ios_base::in is for writing
		ifstream inf(file, ios_base::in);
		if (!inf.is_open()){
			std::cout << "failed to open " << file << '\n';
		}
		//line = current line, words = list of words from the line, count = number of words in the line
		string line;
		string words[128];
		int count;
		
		//pulls out a line at a time
		int currChar=0;
		while (getline(inf, line)) {
			//Used to iterate into defined parsable parts for example can be used to skip comments
			while(line[currChar]!='v' and line[currChar]!='s' and line[currChar]!='f'){
				//printf("%d , %d = %c\n",currChar,line.length(),line[currChar]);
				if(currChar == line.length()){
					printf("asdf %c\n",line[currChar]);
					//Wild GOTO, Arkadaşlar bu gördüğünüz goto kullanımının nadir iyi kullanışlarındandır acayip zattirik.
					goto contin;
				}
				currChar++;
			}
			//This is the place where ve match for words
			switch (line[currChar]){
				case 'v':
				switch(line[currChar+1]){
					case ' ':
						count = getvaluec(words,line,currChar+1);
						for(int i = 0; i<count;i++){cout << words[i] <<'\n';}
						break;
					case 't':
						break;
					case 'n':
						break;
					case 'p':
						break;
				}
				break;
				
			}
			cout << line <<'\n';
			
			contin:
			currChar=0;
		}
		return 0;
	};
};
int main(){
	loads r;
	r.loadobj("Database_test.txt");
	return 0;
}
