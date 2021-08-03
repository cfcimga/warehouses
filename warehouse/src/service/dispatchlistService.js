import axios from 'axios'

export async function getAllDispatchlist() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/dispatchlist/findAllDispatchlist')
    return result.data.data;
}


export async function addDispatchlist(dispatchlist){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/dispatchlist/addDispatchlist', dispatchlist);
    return result.data;
}

export async function editDispatchlist(dispatchlist){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/dispatchlist/editDispatchlist',dispatchlist)
    return result.data;
}

export async function deleteDispatchlist(dpId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/dispatchlist/deleteDispatchlist',{
        params:{
            dpId
        }
    });
    return result.data;
}

export async function updateState(dpState,dpId){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/dispatchlist/updateState',null,{
        params:{
            dpState,
            dpId
        }
    })
    return result.data;
}

export async function findReturnNote(dpState){
    let result = await axios.get('http://localhost:8181/backEndWarehouse/dispatchlist/findReturnNote',{
        params:{
            dpState,
        }
    })
    return result.data.data;
}

export async function getDpByCIdAndState(dpCId){
    let result = await axios.get('http://localhost:8181/backEndWarehouse/dispatchlist/getDpByCIdAndState',{
        params:{
            dpCId,
        }
    })
    return result.data;
}