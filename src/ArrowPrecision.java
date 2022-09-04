public enum ArrowPrecision {
    Perfect(5),
    Great(3),
    Good(1),
    Miss(0);

    int multiplier;
    ArrowPrecision(int m){
        multiplier = m;
    }
}
