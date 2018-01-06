public class SystemHPRN {
    static int commonTime;

    ElementOfList firstMustRun;
    ElementOfList currEndMustRun;
    ElementOfList firstRun;
    ElementOfList currEndRun;
    ElementOfList firstCompleted;
    ElementOfList currEndCompleted;

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

    private void calcPriority(){
        if (firstRun == null)
            return;
        ElementOfList curr = firstRun;
        ElementOfList max = firstRun;
        while (curr != null){
            if ((commonTime - curr.process.comeTime)/curr.process.mustTime > (commonTime - max.process.comeTime)/max.process.mustTime){
                max = curr;
            }
            curr = curr.next;
        }
        if (firstRun == max)
            return;
        ElementOfList previous = firstRun;
        curr = firstRun.next;
        while (curr != max){
            previous = curr;
            curr = curr.next;
        }
        previous.next = max.next;
        curr = firstRun;
        firstRun = max;
        max.next = curr;
    }

    public int start(){
        while (firstMustRun != null || firstRun != null) {
            while (firstMustRun != null && firstMustRun.process.comeTime <= commonTime) {
                addToRun(firstMustRun.process);
                firstMustRun = firstMustRun.next;
            }
            calcPriority();
            if (firstRun != null) {
                while (!firstRun.process.run()) {
                    commonTime++;
                }
                addToCompleted(firstRun.process);
                firstRun.process.calcShtraf(commonTime);
                firstRun.process.print();
                firstRun = firstRun.next;
            } else
                commonTime++;
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
            currEndRun.next = new ElementOfList(process);
            currEndRun = currEndRun.next;
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
