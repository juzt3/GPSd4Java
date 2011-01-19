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

/**
 * 
 * created: 17.01.2011
 * 
 */
public class VersionObject implements IGPSObject {
	
	private String release;
	
	private String rev;
	
	private double protocolMajor;
	
	private double protocolMinor;
	

	/**
	 * @return the release
	 */
	public String getRelease() {
		return this.release;
	}
	
	/**
	 * @param release the release to set
	 */
	public void setRelease(String release) {
		this.release = release;
	}
	
	/**
	 * @return the rev
	 */
	public String getRev() {
		return this.rev;
	}
	
	/**
	 * @param rev the rev to set
	 */
	public void setRev(String rev) {
		this.rev = rev;
	}
	
	/**
	 * @return the protocolMajor
	 */
	public double getProtocolMajor() {
		return this.protocolMajor;
	}
	
	/**
	 * @param protocolMajor the protocolMajor to set
	 */
	public void setProtocolMajor(double protocolMajor) {
		this.protocolMajor = protocolMajor;
	}
	
	/**
	 * @return the protocolMinor
	 */
	public double getProtocolMinor() {
		return this.protocolMinor;
	}
	
	/**
	 * @param protocolMinor the protocolMinor to set
	 */
	public void setProtocolMinor(double protocolMinor) {
		this.protocolMinor = protocolMinor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.protocolMajor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.protocolMinor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((this.release == null) ? 0 : this.release.hashCode());
		result = prime * result + ((this.rev == null) ? 0 : this.rev.hashCode());
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
		VersionObject other = (VersionObject) obj;
		if (Double.doubleToLongBits(this.protocolMajor) != Double.doubleToLongBits(other.protocolMajor)) {
			return false;
		}
		if (Double.doubleToLongBits(this.protocolMinor) != Double.doubleToLongBits(other.protocolMinor)) {
			return false;
		}
		if (this.release == null) {
			if (other.release != null) {
				return false;
			}
		} else if (!this.release.equals(other.release)) {
			return false;
		}
		if (this.rev == null) {
			if (other.rev != null) {
				return false;
			}
		} else if (!this.rev.equals(other.rev)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "VersionObject [release=" + this.release + ", rev=" + this.rev + ", protocolMajor=" + this.protocolMajor + ", protocolMinor=" + this.protocolMinor + "]";
	}
	
}