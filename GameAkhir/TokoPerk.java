package GameAkhir;
import java.util.*;
public class TokoPerk {
    private List<Perk> daftarPerk = Arrays.asList(
        new PerkElegan("Elegan",1),
        new PerkActive("Active",1),
        new PerkCharming("Charming",1)
    );
    private Player pemain;
    public TokoPerk(List<Perk> daftarPerk, Player pemain){
        this.daftarPerk = daftarPerk;
        this.pemain = pemain;
        
    }
    
    public void beliPerk(){
        
    }
}
