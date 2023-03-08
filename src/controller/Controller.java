package controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

import model.Lease;
import model.Property;
import model.PropertyFactory;
import model.Tenant;
import utility.PropertyType;
import view.PropertyView;
import view.TenantView;

public class Controller {
	private PropertyView propertyView;
	private TenantView tenantView;
	private Property propertyModel;
	private Tenant tenantModel;
	public Controller(PropertyView propertyView, TenantView tenantView, Property propertyModel, Tenant tenantModel) {
		this.propertyView = propertyView;
		this.propertyModel = propertyModel;
		this.tenantView = tenantView;
		this.tenantModel = tenantModel;
	}

	public static Date getDateFromUser(Scanner input) {
		System.out.print("Enter day: ");
		int day = input.nextInt();
		
		System.out.print("Enter month: ");
		int month = input.nextInt();
		
		System.out.print("Enter year: ");
		int year = input.nextInt();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		return calendar.getTime();
	}
	
	public void mainFunction() {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Property> properties = new ArrayList<>();
		ArrayList<Tenant> tenants = new ArrayList<>();
		ArrayList<Lease> leases = new ArrayList<>();

		while(true) {
			System.out.println("--------------------------");
			System.out.println("1.  Add a property");
			System.out.println("2.  Add a tenant");
			System.out.println("3.  Rent a unit");
			System.out.println("4.  Display tenants");
			System.out.println("5.  Display properties");
			System.out.println("6.  Display vacant units");
			System.out.println("7.  Display rented units");
			System.out.println("8.  Display paid/not paid rent units");
			System.out.println("9.  Express an interest in a unit");
			System.out.println("10. Display potential tenants and their units");
			System.out.println("11. Display leases that will be ending");
			System.out.println("12. Display all leases");
			System.out.println("13. Exit");
			System.out.print("Your choice:");
			int cmd = scanner.nextInt();
			switch (cmd){
			case 1:
				// Add a property
//				PropertyFactory propertyFactory = new PropertyFactory();
				Map<String, ArrayList<Object>> inputPropertyData = propertyView.getPropertyInfo();
				for (Map.Entry<String, ArrayList<Object>> pair : inputPropertyData.entrySet()) {
					if("A".equals(pair.getKey())) {
						propertyModel = PropertyFactory.createProperty(PropertyType.APARTMENT);
						properties.add(propertyModel.create(pair.getValue()));
					} else if ("C".equals(pair.getKey())) {
						propertyModel = PropertyFactory.createProperty(PropertyType.CONDO);
						properties.add(propertyModel.create(pair.getValue()));
					} else if ("H".equals(pair.getKey())) {
						propertyModel = PropertyFactory.createProperty(PropertyType.HOUSE);
						properties.add(propertyModel.create(pair.getValue()));
					}
				}
				
				break;
			case 2:
				// Add a tenant
				//ArrayList<Lease> LeaseNos = new ArrayList<>();
				//ArrayList<Property> InterestedProps = new ArrayList<>();
				ArrayList<Object> details = new ArrayList<>();
				System.out.println("Enter the Tenant Name:");
				scanner.nextLine();
				String tenantName = scanner.nextLine();
				details.add(tenantName);
				System.out.println("Enter the Tenant Phone No:");
				String phoneNo = scanner.nextLine();
				details.add(phoneNo);
				System.out.println("Enter the Tenant Email:");
				String emailID = scanner.nextLine();
				details.add(emailID);

				Tenant tenant = new Tenant();
				Tenant newTenant = tenant.create(details);
				tenants.add(newTenant);
				System.out.println("Tenant Added Successfully!");
				
				break;
			
			case 3:
				// Rent a unit 
				//so as i understand here i should display all available units and all tenets, then
				//ask user which unit he wants to rent and for which tenent, take that property object and tenet 
				//object and populate the info needed for lease 
				ArrayList<Object> savedInfo = new ArrayList<>();

				System.out.println("Here's the vacant properties.");
				if(properties.size() > 0) {
					propertyView.displayVacantProperty(properties);
				}
				System.out.println();
				System.out.println("Please enter property ID you want to rent.");

				int selectedPropertyID = scanner.nextInt();
				
				Property selectedPropertyObject = propertyView.getObjectByID(selectedPropertyID, properties);
				
				System.out.println();
				System.out.println("Now select which tenant is buying the property.");

				if(tenants.size() > 0) {
					for (int i = 0; i < tenants.size(); i++) {
						if(tenants.get(i).toString() != null) {
							System.out.println((i+1) +". "+tenants.get(i).display());
						}
					}
				} else {
					System.out.println("No tenants found.");
				}

				System.out.println("Enter ID of the Tenant");
				int selectedTenantID = scanner.nextInt();
				Tenant tnt = new Tenant();
				Tenant selectedTenantObject = tnt.getObjectByID(selectedTenantID, tenants);

				System.out.println("Now enter the start date of lease.");
				
				Date startDate = getDateFromUser(scanner);
				savedInfo.add(startDate);

				System.out.println();
				System.out.println("Now enter the ending date of the lease.");
				Date endDate = getDateFromUser(scanner);
				savedInfo.add(endDate);

				System.out.println("Enter the total rent.");
				int rent = scanner.nextInt();
				savedInfo.add(rent);

				scanner.nextLine();
				System.out.println("Is rent paid? (Y/N)");
				String rentPaid = scanner.nextLine();
				boolean choice = true;
				if(rentPaid.equals("Y")) {
					choice = true;
					savedInfo.add(choice);
				}
				else {
					choice = false;
					savedInfo.add(choice);
				}
				savedInfo.add(selectedTenantObject);
				savedInfo.add(selectedPropertyObject);
				Lease lease = new Lease();
				Lease addedLease = lease.create(savedInfo);
				System.out.println("Perfect!");
				leases.add(addedLease);

				//now add that lease to tenant object field.
				selectedTenantObject.setLeases(addedLease);
				//make property unavailable
				selectedPropertyObject.setStatus(false);

				break;
			case 4:
				// Display tenants
				if(tenants.size() > 0) {
					tenantView.displayAllTenants(tenants);
				} else {
					System.out.println("No tenants found.");
				}
				break;
			case 5:
				// Display properties
				if(properties.size() > 0) {
					propertyView.displayProperty(properties);
				} else {
					System.out.println("No properties found.");
				}				
				break;
			case 6:
				// Display vacant units
				if(properties.size() > 0) {
					propertyView.displayVacantProperty(properties);
				}
				break;
			case 7:
				// Display rented units
				if(properties.size() > 0) {
					propertyView.displayRentedProperty(properties);
				}
				break;
			case 8:
				// Display paid/not paid rent units
				if(leases.size() > 0) {
					System.out.println("Rent paid: ");

					for (int i = 0; i < leases.size(); i++) {
						if(leases.get(i).getIsRentPaid() == true) {
							System.out.println(leases.get(i).display());
						}
					}
					System.out.println("Rent Unpaid: ");
					for (int i = 0; i < leases.size(); i++) {
						if(leases.get(i).getIsRentPaid() != true) {
							System.out.println(leases.get(i).display());
						}
					}
				}
				else {
					System.out.println("No leases to show!");
				}
				break;
			case 9:
				// Express an interest in a unit
				String interestedData = tenantView.interestedInAUnit(properties, tenants);
				tenantModel.interestedInAUnit(interestedData, tenants, properties);
				
				break;
			case 10:
				// Display potential tenants and their units
				tenantModel.displayPotentialTenants();
				break;
			case 11:
				// Display leases that will be ending
				int year = LocalDate.now().getYear();
				int mon = LocalDate.now().getMonthValue();
				if(leases.size() > 0) {
					for (int i = 0; i < leases.size(); i++) {
						if((leases.get(i).getLeaseEndDate().getMonth() == mon) && (leases.get(i).getLeaseEndDate().getYear() == year)) {
						System.out.println((i+1) +". Leases Ending::"+ leases.get(i).display());
						}
					}
				}
				else {
					System.out.println("No lease found!");
				}
				break;
			case 12:
				// Display all leases
				if(leases.size() > 0) {
					for (int i = 0; i < leases.size(); i++) {
						System.out.println((i+1) +". "+ leases.get(i).display());
					}
				}
				else {
					System.out.println("No lease found!");
				}
				break;
			case 13:
				// Exit
				scanner.close();
				System.out.println("Exiting...");
				return;
				
			default:
				System.out.println("Enter right value");
			}
		}
	}
}
