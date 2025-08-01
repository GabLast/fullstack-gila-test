import { useState, useEffect } from 'react'
import { getCategories } from '../../api/categories';
import { postMessage } from '../../api/messages';


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
            const { data } = await postMessage(category, message);

            // clear
            setMessage('')
            setCategory(categoryList[0].name) // Reset to first category

            alert("Message sent successfully!");
        } catch (err) {
            console.log(err)
        }

    }

    const fetchCategories = async () => {
        const { data } = await getCategories();
        setCategoryList(data || []);
        setCategory(data[0].name || undefined) // Set default category to first one
    }

    useEffect(() => {
        fetchCategories();
    }, [])

    return (
        <form onSubmit={handleSubmit}>
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
                <input type="submit" value="Submit"
                    className='border-2 border-blue-600 p-2 bg-blue-300' />
            </div>
        </form>

    )
}

export default FormMessage;
