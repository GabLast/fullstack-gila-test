import { useState, useEffect, use } from 'react'
import { CATEGORY_ENDPOINT, MESSAGE_ENDPOINT } from '../config/apiEndPoints';
import { ApiClient } from '../api/ApiClient';

function FormMessage() {

    const [categoryList, setCategoryList] = useState(null)
    const [category, setCategory] = useState(undefined)
    const [message, setMessage] = useState('')

    const validate = () => {
        const errors = [];
        if (!message.trim()) {
            errors.push('Message is required.');
        }
        if (!category) {
            errors.push('Category is required.');
        }

        return errors;
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        const errors = validate();
        if (errors.length > 0) {
            alert(`Please fix the following errors:\n${errors.join('\n')}`);
        }

        try {
            await ApiClient.post(MESSAGE_ENDPOINT, {
                category: category,
                message: message
            })

            // clear
            setMessage('')
            setCategory(categoryList[0].name) // Reset to first category

            alert("Message sent successfully!");
        } catch (err) {
            console.log(err)
        }

    }

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await ApiClient.get(CATEGORY_ENDPOINT + "/findall")
                setCategoryList(response.data)
                setCategory(response.data[0].name) // Set default category to first one
            } catch (error) {
                console.log(error)
            }
        }
        fetchCategories();

    }, [])

    return (
        <div className="flex flex-col grow ml-52 mr-52 mt-10 border-2 p-2 border-blue-300">

            <span className="text-2xl font-semibold text-gray-900 pb-2">Message Form</span>

            <label className='borderp-2 mr-1' htmlFor={"category"}>{"Category: "}</label>
            <select value={category} onChange={e => setCategory(e.target.value)}
                className="border-2 border-blue-300">
                {categoryList && categoryList.map((it) => (
                    <option key={it.id} value={it.name}>{it.name}</option>
                ))}
            </select>

            <br />

            <label className='borderp-2 mr-1' htmlFor={"message"}>{"Message: "}</label>
            <textarea required autoFocus value={message} onChange={e => setMessage(e.target.value)} className="border-2 border-blue-300"
                type="input" name="message" id="message" rows={4} cols={50} />

            <br />
            <input type="submit" value="Submit" onClick={handleSubmit}
                className='border-2 border-blue-600 p-2 bg-blue-300' />


        </div>
    )
}

export default FormMessage;
