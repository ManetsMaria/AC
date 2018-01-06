public class ComplexSystem {
    ElementOfList firstMustRun;
    ElementOfList currEndMustRun;
    ElementOfList firstCompleted;
    ElementOfList currEndCompleted;

    int kol_vo = 1;

    final int N = 2;

    final int kvant = 50;

    int commonTime;

    private void addToMainRun(Process process){
        if (MainSystem.firstRun == null) {
            MainSystem.firstRun = new ElementOfList(process);
            MainSystem.currEndRun = MainSystem.firstRun;
        }
        else{
            MainSystem.currEndRun.next = new ElementOfList(process);
            MainSystem.currEndRun = MainSystem.currEndRun.next;
        }
    }

    private void addToBackRun(Process process){
        if (BackSystem.firstRun == null) {
            BackSystem.firstRun = new ElementOfList(process);
            BackSystem.currEndRun = BackSystem.firstRun;
        }
        else{
            BackSystem.currEndRun.next = new ElementOfList(process);
            BackSystem.currEndRun = BackSystem.currEndRun.next;
        }
    }

    public void addToMust(Process process){
        if (firstMustRun == null) {
            firstMustRun = new ElementOfList(process.copy());
            currEndMustRun = firstMustRun;
        }
        else{
            currEndMustRun.next = new ElementOfList(process.copy());
            currEndMustRun = currEndMustRun.next;
        }
    }


    public int start(){
        if (firstMustRun == null)
            return 0;
        while (firstMustRun != null || MainSystem.firstRun != null){
            while (firstMustRun != null && firstMustRun.process.comeTime <= commonTime){
                addToMainRun(firstMustRun.process);
                firstMustRun = firstMustRun.next;
            }
            if (MainSystem.firstRun == null){
                commonTime++;
                continue;
            }
            int start = 0;
            while (start < kvant && !MainSystem.firstRun.process.run()){
                commonTime++;
                start++;
                MainSystem.firstRun.n++;
            }
            if (MainSystem.firstRun.process.completed) {
                addToCompleted(MainSystem.firstRun.process);
                MainSystem.firstRun.process.calcShtraf(commonTime);
                MainSystem.firstRun.process.print();
                MainSystem.firstRun = MainSystem.firstRun.next;
            }
            if (MainSystem.firstRun != null && MainSystem.firstRun.n > N){
                addToBackRun(MainSystem.firstRun.process);
                MainSystem.firstRun = MainSystem.firstRun.next;
            }
        }
        while (BackSystem.firstRun != null){
            while (!BackSystem.firstRun.process.run()) {
                commonTime++;
            }
            addToCompleted(BackSystem.firstRun.process);
            BackSystem.firstRun.process.calcShtraf(commonTime);
            BackSystem.firstRun.process.print();
            BackSystem.firstRun = BackSystem.firstRun.next;
        }
        int commonStraf = 0;
        while (firstCompleted != null){
            commonStraf+= firstCompleted.process.shtraf;
            firstCompleted = firstCompleted.next;
        }
        return  commonStraf;
    }
    static class MainSystem{
        static ElementOfList firstRun;
        static ElementOfList currEndRun;
    }
    static class BackSystem{
        static ElementOfList firstRun;
        static ElementOfList currEndRun;
    }
    private void addToCompleted(Process process){
        if (firstCompleted == null) {
            firstCompleted = new ElementOfList(process);
            currEndCompleted = firstCompleted;
        }
        else{
            currEndCompleted.next = new ElementOfList(process);
            currEndCompleted = currEndCompleted.next;
        }
    }
}
