package sapp.model.vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

@Component
public class VehicleJdbcDao implements VehicleDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// ==== ==== ==== ==== ==== ===== ===== ==== ===== ==== ===== I N S E R T 
	// ============================================= light insert
	@Override
	public void insert(Vehicle vehicle) {
		String sql = "INSERT INTO VEHICLE (VEHICLE_NO, COLOR, WHEEL, SEAT) " + "VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] { vehicle.getVehicleNo(), vehicle.getColor(), vehicle.getWheel(), vehicle.getSeat() });
	}

	// ============================================= preparet statement creator
	public void insertPreparedStatementCreator(Vehicle vehicle) { 
		jdbcTemplate.update(new InsertVehicleStatementCreator(vehicle));
	}
	private class InsertVehicleStatementCreator implements PreparedStatementCreator {
		private Vehicle vehicle;

		public InsertVehicleStatementCreator(Vehicle vehicle) {
			this.vehicle = vehicle;
		}

		@Override
		public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
			String sql = "INSERT INTO VEHICLE (VEHICLE_NO, COLOR, WHEEL, SEAT) " + "VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, vehicle.getVehicleNo());
			ps.setString(2, vehicle.getColor());
			ps.setInt(3, vehicle.getWheel());
			ps.setInt(4, vehicle.getSeat());
			return ps;
		}
	}
	
	// ============================================= batch insert BatchPreparedStatementSetter
	@Override
	public void insertBatch(final List<Vehicle> vehicles) {
		String sql = "INSERT INTO VEHICLE (VEHICLE_NO, COLOR, WHEEL, SEAT) " + "VALUES (?, ?, ?, ?)";
		
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public int getBatchSize() {
				return vehicles.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Vehicle vehicle = vehicles.get(i);
				ps.setString(1, vehicle.getVehicleNo());
				ps.setString(2, vehicle.getColor());
				ps.setInt(3, vehicle.getWheel());
				ps.setInt(4, vehicle.getSeat());
			}
		});
	}
	// ==== ==== ==== ==== ==== ===== ===== ==== ===== ==== ===== F I N D  
	// ============================================= bean property row mapper
	@Override
	public Vehicle findByVehicleNo(String vehicleNo) {
	    String sql = "SELECT * FROM VEHICLE WHERE VEHICLE_NO = ?";
	    
	    BeanPropertyRowMapper<Vehicle> vehicleRowMapper = BeanPropertyRowMapper.newInstance(Vehicle.class);
	    Vehicle vehicle = jdbcTemplate.queryForObject(sql, vehicleRowMapper, vehicleNo);
	    return vehicle;
	}
	// ============================================= row mapper
	public Vehicle findByVehicleNoRowMapper(String vehicleNo) {
		String sql = "SELECT * FROM VEHICLE WHERE VEHICLE_NO = ?";
		
		Vehicle vehicle = (Vehicle) jdbcTemplate.queryForObject(
				sql, 
				new Object[] { vehicleNo },
				new VehicleRowMapper());
		return vehicle;
	}
	public class VehicleRowMapper implements RowMapper<Vehicle> {
		@Override
		public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
			Vehicle vehicle = new Vehicle();
			vehicle.setVehicleNo(rs.getString("VEHICLE_NO"));
			vehicle.setColor(rs.getString("COLOR"));
			vehicle.setWheel(rs.getInt("WHEEL"));
			vehicle.setSeat(rs.getInt("SEAT"));
			return vehicle;
		}
	}
	// ============================================= ROW CALLBACK HANDLER
	
	public Vehicle findByVehicleNoRowCallbackHandler(String vehicleNo) {
		String sql = "SELECT * FROM VEHICLE WHERE VEHICLE_NO = ?";
		
		final Vehicle vehicle = new Vehicle();
		jdbcTemplate.query(sql, new Object[] { vehicleNo }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				vehicle.setVehicleNo(rs.getString("VEHICLE_NO"));
				vehicle.setColor(rs.getString("COLOR"));
				vehicle.setWheel(rs.getInt("WHEEL"));
				vehicle.setSeat(rs.getInt("SEAT"));
			}
		});
		return vehicle;
	}
	// ============================================= JDBC	
	public Vehicle findByVehicleNoBasic(String vehicleNo) {
		String sql = "SELECT * FROM VEHICLE WHERE VEHICLE_NO = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, vehicleNo);
			Vehicle vehicle = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				vehicle = new Vehicle(rs.getString("VEHICLE_NO"), rs.getString("COLOR"), rs.getInt("WHEEL"),
						rs.getInt("SEAT"));
			}
			rs.close();
			ps.close();
			return vehicle;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	// ==== ==== ==== ==== ==== ===== ===== ==== ===== ==== ===== F I N D ==== A L L
	// =============================================== basic
	public List<Vehicle> findAllBasic() {
		String sql = "SELECT * FROM VEHICLE";
		
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : rows) {
			Vehicle vehicle = new Vehicle();
			vehicle.setVehicleNo((String) row.get("VEHICLE_NO"));
			vehicle.setColor((String) row.get("COLOR"));
			vehicle.setWheel((Integer) row.get("WHEEL"));
			vehicle.setSeat((Integer) row.get("SEAT"));
			vehicles.add(vehicle);
		}
		return vehicles;
	}

	// =============================================== with row mapper
	public List<Vehicle> findAll() {
		String sql = "SELECT * FROM VEHICLE";
		
		RowMapper<Vehicle> rm = BeanPropertyRowMapper.newInstance(Vehicle.class);
		List<Vehicle> vehicles = jdbcTemplate.query(sql, rm);
		return vehicles;
	}

	// single result single column
	public String getColor(String vehicleNo) {
		String sql = "SELECT COLOR FROM VEHICLE WHERE VEHICLE_NO = ?";
		
		String color = (String) jdbcTemplate.queryForObject(sql, new Object[] { vehicleNo }, String.class);
		return color;
	}

	public int countAll() {
		String sql = "SELECT COUNT(*) FROM VEHICLE";
		
		int count = jdbcTemplate.queryForObject(sql, null, Integer.class);
		return count;
	}

	@Override
	public void update(Vehicle vehicle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Vehicle vehicle) {
		// TODO Auto-generated method stub

	}

}