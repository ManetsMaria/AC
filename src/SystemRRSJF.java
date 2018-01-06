public class SystemRRSJF {
    static int commonTime;
    final int kvant = 10;

    ElementOfList firstMustRun;
    ElementOfList currEndMustRun;
    ElementOfList firstRun;
    ElementOfList currEndRun;
    ElementOfList firstCompleted;
    ElementOfList currEndCompleted;

    int kol_vo = 1;

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
        while (firstMustRun != null || firstRun != null){
            while (firstMustRun != null && firstMustRun.process.comeTime <= commonTime){
                addToRun(firstMustRun.process);
                firstMustRun = firstMustRun.next;
            }
            if (firstRun == null){
                commonTime++;
                continue;
            }
            int start = 0;
            while (start < kvant && !firstRun.process.run()){
                commonTime++;
                start++;
            }
            if (firstRun.process.completed) {
                addToCompleted(firstRun.process);
                firstRun.process.calcShtraf(commonTime);
                firstRun.process.print();
                firstRun = firstRun.next;
            }
        }
        int commonStraf = 0;
        while (firstCompleted != null){
            commonStraf+= firstCompleted.process.shtraf;
            firstCompleted = firstCompleted.next;
        }
        return  commonStraf;
    }
    private void addToRun(Process process){
        if (firstRun == null) {
            firstRun = new ElementOfList(process);
            currEndRun = firstRun;
        }
        else{
            if (firstRun.process.mustTime - firstRun.process.runTime > process.mustTime){
                Process temp = firstRun.process;
                firstRun.process = process;
                process = temp;
            }
            ElementOfList curr = firstRun;
            int count = 0;
            while (curr != null){
                if (curr.process.runTime != 0)
                    count++;
                curr = curr.next;
            }
            kol_vo = Math.max(kol_vo, count);
            curr = firstRun;
            while (curr != null && curr.process.mustTime - curr.process.runTime < process.mustTime){
                curr = curr.next;
            }
            if (curr == null) {
                currEndRun.next = new ElementOfList(process);
                currEndRun = currEndRun.next;
            }
            else{
                ElementOfList elementOfList = new ElementOfList(process);
                elementOfList.next = curr.next;
                curr.next = elementOfList;
            }
        }
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
