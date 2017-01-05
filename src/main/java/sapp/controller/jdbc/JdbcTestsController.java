package sapp.controller.jdbc;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sapp.model.vehicle.Vehicle;
import sapp.model.vehicle.VehicleDao;

@Controller
public class JdbcTestsController {
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	private VehicleDao vehicleDao;
	@Autowired
	public JdbcTestsController(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	@RequestMapping("/ji")
	public String insert() {
		Vehicle vehicle1 = new Vehicle("TEM0001", "Czerwony", 4, 4);
		Vehicle vehicle2 = new Vehicle("TEM0002", "Niebieski", 4, 4);
		Vehicle vehicle3 = new Vehicle("TEM0003", "Czarny", 4, 6);
		vehicleDao.insert(vehicle1);
		vehicleDao.insertBatch(Arrays.asList(new Vehicle[] { vehicle2, vehicle3 }));

		return "jdbc";
	}

	@RequestMapping("/jr")
	public String result(Model model) {
		Vehicle vehicle1 = vehicleDao.findByVehicleNo("TEM0001");
		model.addAttribute("vehicle1", vehicle1.toString());
		
		List<Vehicle> vehicles = vehicleDao.findAll(); 
		model.addAttribute("vehicles",vehicles);
		
		
		int count = vehicleDao.countAll();
		String color = vehicleDao.getColor("TEM0001");
		model.addAttribute("count",count);
		model.addAttribute("color",color);
		
		return "jdbc";
	}
	
}