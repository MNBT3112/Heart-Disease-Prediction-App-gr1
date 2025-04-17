@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Retrieve input values and provide default values if missing
        cp = int(request.form.get('cp', 0))  # Default to 0
        thalach = int(request.form.get('thalach', 0))  # Default to 0
        slope = int(request.form.get('slope', 0))  # Default to 0
        restecg = int(request.form.get('restecg', 0))  # Default to 0
        chol = float(request.form.get('chol', 0.0))  # Default to 0.0
        trestbps = float(request.form.get('trestbps', 0.0))  # Default to 0.0
        fbs = int(request.form.get('fbs', 0))  # Default to 0
        oldpeak = float(request.form.get('oldpeak', 0.0))  # Default to 0.0

        # Build the input array
        input_query = np.array([[cp, thalach, slope, restecg, chol, trestbps, fbs, oldpeak]])

        # Perform predictions
        prediction = model.predict(input_query)[0]
        probabilities = model.predict_proba(input_query)[0]
        confidence = probabilities[prediction] * 100  # Confidence in percentage

        # Prepare and return the response
        return jsonify({
            'heart_disease_prediction': str(prediction),
            'confidence_percentage': f"{confidence:.2f}%",
            'description': 'Heart disease likely' if prediction == 1 else 'No heart disease detected'
        })

    except Exception as e:
        # Catch all errors and return them as part of the response
        return jsonify({'error': f"An unexpected error occurred: {str(e)}"}), 500