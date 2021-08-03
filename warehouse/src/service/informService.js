import axios from 'axios'

export async function getAllInform() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/inform/findAllInform')
    return result.data.data;
}

export async function addInform(inform){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/inform/addInform', inform);
    return result.data;
}
export async function addMdInform(MdInform){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/mdform/addMdform', MdInform);
    return result.data;
}

export async function deleteInform(fId){
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/inform/deleteInform', {
        params:{
            fId
        }
    });
    return result.data;
}

