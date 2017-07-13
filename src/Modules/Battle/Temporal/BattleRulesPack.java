package Modules.Battle.Temporal;

/**
 *
 * @author Nicolás Balcero
 */
public class BattleRulesPack {
    private int team_size;
    private int time_limit; //minutos
    private int kill_victory; // muertes para ganar
    
    public BattleRulesPack(int team_size, int time_limit, int kill_victory){
        this.team_size=team_size;
        this.kill_victory=kill_victory;
        this.time_limit=time_limit;
    }

    public int getTeam_size() {
        return team_size;
    }

    public void setTeam_size(int team_size) {
        this.team_size = team_size;
    }

    public int getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
    }

    public int getKill_victory() {
        return kill_victory;
    }

    public void setKill_victory(int kill_victory) {
        this.kill_victory = kill_victory;
    }

}
