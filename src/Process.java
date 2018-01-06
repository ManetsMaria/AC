public class Process{

    int comeTime;
    int mustTime;
    int runTime;
    int outTime;
    int priority;
    boolean completed;
    int number;
    int shtraf;

    Process (int comeTime, int number, int mustTime){
        this.comeTime = comeTime;
        this.number = number;
        this.mustTime = mustTime;
    }

    public boolean run() {
        runTime++;
        if (!(runTime < mustTime)) {
            completed = true;
        }
        return completed;
    }
    public void calcShtraf(int outTime){
        this.outTime = outTime;
        shtraf = Math.abs(outTime - comeTime - mustTime);
    }
    public Process copy(){
        return new Process(comeTime, number, mustTime);
    }
    public void print(){
        System.out.println("comeTime: "+comeTime+" outTime: "+outTime+" mustrun: "+mustTime+" run: "+runTime+" shtraf: "+shtraf+" dly spravedlivosti: "+(double)(outTime - comeTime)/mustTime);
    }
}
