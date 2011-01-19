/**
 * Copyright 2011 Thorsten Höger, Taimos GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.taimos.gpsd4java.types;

import java.util.List;

/**
 * 
 * created: 18.01.2011
 * 
 */
public class DevicesObject implements IGPSObject {
	
	private List<DeviceObject> devices;
	

	/**
	 * list of devices
	 * 
	 * @return the devices
	 */
	public List<DeviceObject> getDevices() {
		return this.devices;
	}
	
	/**
	 * list of devices
	 * 
	 * @param devices the devices to set
	 */
	public void setDevices(List<DeviceObject> devices) {
		this.devices = devices;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.devices == null) ? 0 : this.devices.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		DevicesObject other = (DevicesObject) obj;
		if (this.devices == null) {
			if (other.devices != null) {
				return false;
			}
		} else if (!this.devices.equals(other.devices)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "DevicesObject [devices=" + this.devices.size() + "]";
	}
	
}