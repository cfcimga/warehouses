import axios from 'axios'

export async function getAllCustomer() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/customer/findAllCustomer')
    return result.data.data;
}

export async function addCustomer(customer){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/customer/addCustomer',customer);
    return result.data;
}

export async function editCustomer(customer){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/customer/editCustomer',customer)
    return result.data;
}

export async function deleteCustomer(ctmId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/customer/deleteCustomer/'+ctmId);
    return result.data;
}