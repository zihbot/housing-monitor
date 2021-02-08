import { useEffect, useState } from "react";
import { KeyValuePair } from "./model";

export interface HouseProperties {
    data: KeyValuePair[];
}

const useGetHouseData = () => {
    const [result, setResult] = useState<HouseProperties>({ data: [] });

    useEffect(() => {
        fetch('https://19dd62d4-b2bd-417f-96b6-12f45e56fbee.mock.pstmn.io/rest/pairs?url=https')
            .then(r => r.json())
            .then(r => setResult({ data: r }) );
    }, []);

    return result;
};
export default useGetHouseData;