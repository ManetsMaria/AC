public class SystemSRR {
    static int commonTime;
    final int kvant = 10;
    final int BPrority = 20;
    final int P0Priority = 1;
    final int APiority = 30;

    ElementOfList firstMustRun;
    ElementOfList currEndMustRun;
    ElementOfList firstRun;
    ElementOfList currEndRun;
    ElementOfList firstCompleted;
    ElementOfList currEndCompleted;

    int kol_vo;

    public void addToMust(Process process){
        if (firstMustRun == null) {
            firstMustRun = new ElementOfList(process.copy());
            firstMustRun.process.priority = P0Priority;
            currEndMustRun = firstMustRun;
        }
        else{
            currEndMustRun.next = new ElementOfList(process.copy());
            currEndMustRun.next.process.priority = P0Priority;
            currEndMustRun = currEndMustRun.next;
        }
    }

    private void calcPriority(){
        if (firstRun == null)
            return;
        ElementOfList curr = firstRun;
        int count = 0;
        while (curr != null){
            if (curr.process.runTime == 0)
                curr.process.priority += APiority;
            else {
                curr.process.priority += BPrority;
                count++;
            }
            curr = curr.next;
        }
        if (count > kol_vo)
            kol_vo = count;
        curr = firstRun;
        ElementOfList max = firstRun;
        while (curr != null){
            if (curr.process.priority > max.process.priority){
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
        kol_vo = 1;
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
            calcPriority();
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
