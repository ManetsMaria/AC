import java.util.ArrayList;

public class Demo {

    public static void main(String [] args){
        SystemHPRN systemHPRN = new SystemHPRN();
        SystemFCFO systemFCFO = new SystemFCFO();
        SystemRRSJF systemRRSJF = new SystemRRSJF();
        SystemSRR systemSRR = new SystemSRR();
        ComplexSystem complexSystem = new ComplexSystem();
        for (int i = 1; i < 6; i++){
            //Process process = new Process(i*20, i, (6-i)*55);
            //Process process = new Process(i*10, i , i*20);
            //Process process = new Process(i*20, i , (10-i)*55);
            Process process = new Process(i*20, i , 55);
            systemFCFO.addToMust(process);
            systemHPRN.addToMust(process);
            systemRRSJF.addToMust(process);
            systemSRR.addToMust(process);
            complexSystem.addToMust(process);
        }
        System.out.println(systemFCFO.start() +" "+1);
        System.out.println(systemHPRN.start()+" "+1);
        System.out.println(systemRRSJF.start() +" "+systemRRSJF.kol_vo);
        System.out.println(systemSRR.start()+" "+systemSRR.kol_vo);
        System.out.println(complexSystem.start()+" "+systemSRR.kol_vo);
    }
}
