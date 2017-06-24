package it.polito.tdp.babs;

import java.time.LocalDate;
import java.util.List;

import it.polito.tdp.babs.model.Model;
import it.polito.tdp.babs.model.Simulator;
import it.polito.tdp.babs.model.Station;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class BabsController {

	private Model model ;
    @FXML
    private Slider sliderK;

    @FXML
    private TextArea txtResult;

    @FXML
    private DatePicker pickData;

    @FXML
    void doContaTrip(ActionEvent event) {
    	
    	LocalDate data = pickData.getValue() ;
    	if(data==null)
    		txtResult.setText("ERRORE: Selezionare una data");
    	else{
    		List<Station> stazioni = model.getTrip(data) ;
    		if(stazioni == null)
    			txtResult.setText("ERRORE: Non ci sono trip per questa dataa");
    		else{
    			txtResult.clear();
    			for(Station s : stazioni){
    				int arrivi = s.getArrivi();
    				int partenze = s.getPartenze () ;
    				txtResult.appendText(s+": arrivi "+arrivi+" partenze "+partenze+"\n");
    				}
    			}
    		}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	LocalDate data = pickData.getValue() ;
    	if(data==null)
    		txtResult.setText("ERRORE: Selezionare una data");
    	double k = sliderK.getValue();
    	if(k==0)
    		txtResult.appendText("ERRORE: selezionare un valore per K");
    	else{
    		if(data.getDayOfWeek().ordinal()==5 || data.getDayOfWeek().ordinal()==6){
    			txtResult.setText("ERRORE: selezionare un giorno feriale");
    		}
    		else{
    			Simulator s = model.getSimulator(data, k);
    			txtResult.setText("Tot prese mancate: "+s.getPreseMancate()+"\n Tot ritorni mancati: "
    					+s.getRitorniMancati());
    			
    		}
    	}
    	
    }

	public void setModel(Model model) {
		
		this.model = model ; 
		
	}

}
